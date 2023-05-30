using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ArbolBinB
{
    class Program
    {
        static void Main(string[] args)
        {
            ArbolBinarioBusqueda abo = new ArbolBinarioBusqueda();
            abo.Insertar(100);
            abo.Insertar(50);
            abo.Insertar(25);
            abo.Insertar(75);
            abo.Insertar(150);
            Console.WriteLine("Impresion preorden: ");
            abo.ImprimirPre();
            Console.WriteLine("Impresion entreorden: ");
            abo.ImprimirEntre();
            Console.WriteLine("Impresion postorden: ");
            abo.ImprimirPost();
            Console.ReadKey();
        }
    }
}
