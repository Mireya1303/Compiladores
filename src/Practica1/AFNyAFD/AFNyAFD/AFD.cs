using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFNyAFD
{
    class AFD : Automata
    {
        public AFD()
        {
        }

        public AFD(int inicio, int[] finales, int[] estados, List<string> transicion) : base(inicio, finales, estados, transicion)
        {
        }
    }
}
