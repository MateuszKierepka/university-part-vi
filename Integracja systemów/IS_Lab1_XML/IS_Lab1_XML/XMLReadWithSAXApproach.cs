using System.Xml;
internal class XMLReadWithSAXApproach
{
    internal static void Read(string xmlpath)
    {
        // konfiguracja początkowa dla XmlReadera
        XmlReaderSettings settings = new XmlReaderSettings();
        settings.IgnoreComments = true;
        settings.IgnoreProcessingInstructions = true;
        settings.IgnoreWhitespace = true;

        // odczyt zawartości dokumentu
        XmlReader reader = XmlReader.Create(xmlpath, settings);

        // zmienne pomocnicze
        int count = 0;
        string postac = "";
        string sc = "";

        var slownik = new Dictionary<string, HashSet<string>>();

        var kremy = new Dictionary<string, int>();
        var tabletki = new Dictionary<string, int>();

        reader.MoveToContent();

        // analiza każdego z węzłów dokumentu
        while (reader.Read())
        {
            if (reader.NodeType == XmlNodeType.Element && reader.Name == "produktLeczniczy")
            {
                postac = reader.GetAttribute("postac");
                sc = reader.GetAttribute("nazwaPowszechnieStosowana");
                string podmiot = reader.GetAttribute("podmiotOdpowiedzialny");
                
                if (postac == "Krem" && sc == "Mometasoni furoas")
                    count++;

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
                } else if (postac == "Tabletki" || postac == "Tabletki powlekane")
                {
                    if (!tabletki.ContainsKey(podmiot))
                    {
                        tabletki[podmiot] = 0;
                    }
                    tabletki[podmiot]++;
                }
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
        Console.WriteLine("Podmiot ktory produkuje najwiecej kremow: {0} ({1})", maxKremy.Key, maxKremy.Value);
        Console.WriteLine("Podmiot ktory produkuje najwiecej tabletek: {0} ({1})", maxTabletki.Key, maxTabletki.Value);

        var top3Kremy = kremy.OrderByDescending(k => k.Value).Take(3);
        Console.WriteLine("Trzy podmioty produkujace najwiecej kremow:");
        foreach (var item in top3Kremy)
        {
            Console.WriteLine("{0}: {1}", item.Key, item.Value);
        }
    }
}