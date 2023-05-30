using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFNyAFD
{
    class AFN : Automata
    {
        public AFN()
        {
        }

        public AFN(int inicio, int[] finales, int[] estados, List<string> transicion) : base(inicio, finales, estados, transicion)
        {
        }
    }
}
