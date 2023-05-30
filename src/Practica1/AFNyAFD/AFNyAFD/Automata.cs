using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFNyAFD
{
    class Automata
    {
        int Inicio;
        List<int> Finales;
        List<int> Estados;
        List<string> Transicion;
        char[] Alfabeto;

        public Automata()
        {
            Inicio=1;
            Finales = new List<int>();
            Estados = new List<int>();
            Transicion = new List<string>();
            Alfabeto = new char[27];
            LlenarAlfabeto();
        }
        private void LlenarAlfabeto()
        {
            for (int i = 0; i < 26; i++)
            {
                int a = i + 97;
                Alfabeto[i] = (char)a;
            }
            Alfabeto[26] = 'E';
        }

        public Automata(int inicio, List<int> finales, List<int> estados, List<string> transicion)
        {
            Inicio = inicio;
            Finales = finales;
            Estados = estados;
            Transicion = transicion;
        }

        public Automata Cargar_desde(String nombre)
        {
            string[] lines = System.IO.File.ReadAllLines(nombre);
            Console.WriteLine("Contenido de" + nombre + " = ");
            foreach (string line in lines)
            {
                // Use a tab to indent each line of the file.
                Console.WriteLine("\t" + line);
            }
            Console.WriteLine("Press any key to exit.");
            Console.ReadKey();

            Automata a1 = new Automata();
            AsignarValores(lines);
            return a1;
        }

        public Automata AsignarValores(String[] lines)
        {
            Automata a1 = new Automata();

            int encontrarInicial=0, estadoInicial, encontrarFinal, estadoFin;
            String buscarInicial = lines[0], numeroIni=null, buscarFinal=lines[1], numerosFin=null;
            String numericString = String.Empty;

            encontrarInicial = buscarInicial.IndexOf(": ");
            numeroIni=buscarInicial.Substring(encontrarInicial + 2);
            bool conversion = Int32.TryParse(numeroIni, out estadoInicial);
            if (conversion)
            {
                a1.Inicio = estadoInicial;
            }

            encontrarFinal = buscarFinal.IndexOf(": ");
            numerosFin = buscarFinal.Substring(encontrarFinal + 2);
            foreach (char c in numerosFin)
            {
                if(c>='0' && c <= '9')
                {
                    numericString= String.Concat(numericString, c);
                }
                else
                {
                    bool conversionF = Int32.TryParse(numericString, out estadoFin);
                    a1.Finales.Add(estadoFin);
                    numericString = String.Empty;
                }
            }



            return a1;
        }

        public void Guardar_en(String nombre)
        {
        }

        public void Agregar_transicion(int inicio, int fin, char simbolo)
        {
        }

        public int Obtener_inicial()
        {
            return 0;
        }

        public int[] Obtener_finales
        {
        
        }

        public void Establecer_inicial(int estado)
        {
        }

        public void Establecer_final(int estado)
        {
        }

        public bool EsAFN()
        {
            return true;
        }

        public bool EsAFD()
        {
            return true;
        }

        public bool Acepta(String cadena)
        {
            return true;
        }

        public String Generar_cadena()
        {
            string cadena = null;
            return cadena;
        }
    }
}
