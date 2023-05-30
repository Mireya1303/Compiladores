using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFNyAFD
{
    class Program
    {
        static void Main(string[] args)
        {
            MenuEleccion miMenu = new MenuEleccion();

            String opcion=null;
            opcion=miMenu.ElegirOpcion();
            miMenu.LlamarMetodo(opcion);
        }
    }
}
