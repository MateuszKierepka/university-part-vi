using System;
using System.Threading.Tasks;
using ServiceReference1;

class Program
{
    static async Task Main(string[] args)
    {
        Console.WriteLine("My First SOAP Client!");
        MyFirstSOAPInterfaceClient client = new MyFirstSOAPInterfaceClient();
        string text = await client.getHelloWorldAsStringAsync("Karol");
        Console.WriteLine(text);

        long daysBetween = await client.getDaysBetweenDatesAsync("03-04-2025 15:00", "12-03-2025 15:00");
        Console.WriteLine(daysBetween + " DNI RÓŻNICY");
    }
}