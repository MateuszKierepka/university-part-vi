using System.Xml;
using System.Xml.XPath;
internal class XMLReadWithXLSTDOM
{
    internal static void Read(string xmlpath)
    {
        XPathDocument document = new XPathDocument(xmlpath);
        XPathNavigator navigator = document.CreateNavigator();

        XmlNamespaceManager manager = new XmlNamespaceManager(navigator.NameTable);
        manager.AddNamespace("x", "http://rejestrymedyczne.ezdrowie.gov.pl/rpl/eksport-danych-v1.0");

        XPathExpression query = navigator.Compile("/x:produktyLecznicze/x:produktLeczniczy[@postac='Krem' and @nazwaPowszechnieStosowana='Mometasoni furoas']");
        query.SetContext(manager);

        int count = navigator.Select(query).Count;

        var slownik = new Dictionary<string, HashSet<string>>();
        var kremy = new Dictionary<string, int>();
        var tabletki = new Dictionary<string, int>();

        XPathExpression allProductsQuery = navigator.Compile("/x:produktyLecznicze/x:produktLeczniczy");
        allProductsQuery.SetContext(manager);

        XPathNodeIterator nodes = navigator.Select(allProductsQuery);
        while (nodes.MoveNext())
        {
            XPathNavigator node = nodes.Current;
            string postac = node.GetAttribute("postac", "");
            string sc = node.GetAttribute("nazwaPowszechnieStosowana", "");
            string podmiot = node.GetAttribute("podmiotOdpowiedzialny", "");

            if (!slownik.ContainsKey(sc))
            {
                slownik[sc] = new HashSet<string>();
            }
            slownik[sc].Add(postac);

            if (postac == "Krem")
            {
                if (!kremy.ContainsKey(podmiot))
                {
                    kremy[podmiot] = 0;
                }
                kremy[podmiot]++;
            }
            else if (postac == "Tabletki" || postac == "Tabletki powlekane")
            {
                if (!tabletki.ContainsKey(podmiot))
                {
                    tabletki[podmiot] = 0;
                }
                tabletki[podmiot]++;
            }
        }

        int count2 = 0;
        foreach (var d in slownik)
        {
            if (d.Value.Count > 1)
                count2++;
        }
        var maxKremy = kremy.OrderByDescending(k => k.Value).FirstOrDefault();
        var maxTabletki = tabletki.OrderByDescending(t => t.Value).FirstOrDefault();

        Console.WriteLine("Liczba produktów leczniczych w postaci kremu, których jedyną substancją czynną jest Mometasoni furoas: {0}", count);
        Console.WriteLine("Liczba produktow leczniczych pod ta sama nazwa powszechnie stosowana o roznych postaciach: {0}", count2);
        Console.WriteLine("Producent(y) z największą ilością kremów: {0} ({1})", maxKremy.Key, maxKremy.Value);
        Console.WriteLine("Producent(y) z największą ilością tabletek: {0} ({1})", maxTabletki.Key, maxTabletki.Value);

        var top3Kremy = kremy.OrderByDescending(k => k.Value).Take(3);
        Console.WriteLine("Trzej producenci z największą ilością kremów:");
        foreach (var item in top3Kremy)
        {
            Console.WriteLine("{0}: {1}", item.Key, item.Value);
        }
    }
}