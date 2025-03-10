using System.Xml;

internal class XMLReadWithDOMApproach
{
    internal static void Read(string xmlpath)
    {
        var slownik = new Dictionary<string, HashSet<string>>();
        var kremy = new Dictionary<string, int>();
        var tabletki = new Dictionary<string, int>();

        // odczyt zawartości dokumentu
        XmlDocument doc = new XmlDocument();
        doc.Load(xmlpath);
        string postac;
        string sc;
        int count = 0;
        var drugs = doc.GetElementsByTagName("produktLeczniczy");
        foreach (XmlNode d in drugs)
        {
            postac = d.Attributes.GetNamedItem("postac").Value;
            sc = d.Attributes.GetNamedItem("nazwaPowszechnieStosowana").Value;
            string podmiot = d.Attributes.GetNamedItem("podmiotOdpowiedzialny").Value;

            if (!slownik.ContainsKey(sc))
            {
                slownik[sc] = new HashSet<string>();
            }
            slownik[sc].Add(postac);

            if (postac == "Krem" && sc == "Mometasoni furoas")
                count++;

            if (postac == "Krem")
            {
                if (!kremy.ContainsKey(podmiot))
                {
                    kremy[podmiot] = 0;
                }
                kremy[podmiot]++;
            } else if (postac == "Tabletki" || postac == "Tabletki powlekane")
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
        Console.WriteLine("Podmiot ktory produkuje najwięcej kremow: {0} ({1})", maxKremy.Key, maxKremy.Value);
        Console.WriteLine("Podmiot ktory produkuje najwiecej tabletek: {0} ({1})", maxTabletki.Key, maxTabletki.Value);

        var top3Kremy = kremy.OrderByDescending(k => k.Value).Take(3);
        Console.WriteLine("Trzy podmioty produkujace najwiecej kremow:");
        foreach (var item in top3Kremy)
        {
            Console.WriteLine("{0}: {1}", item.Key, item.Value);
        }
    }
}