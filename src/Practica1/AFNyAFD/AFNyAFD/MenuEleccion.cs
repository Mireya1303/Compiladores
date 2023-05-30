using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFNyAFD
{
    class MenuEleccion
    {
        public string ElegirOpcion()
        {
            Console.WriteLine("Menu principal, seleccione una opcion:");
            Console.WriteLine("1. Cargar archivo\n" +
                              "2. Guardar archivo\n" +
                              "3. Agregar transicion\n" +
                              "4. Eliminar transicion\n" +
                              "5. Obtener estado inicial\n" +
                              "6. Obtener estados finales\n" +
                              "7. Establecer estado inicial\n" +
                              "8. Establecer estado final\n" +
                              "9. Es AFN?\n" +
                              "10. Es AFD?\n" +
                              "11. Acepta la cadena?\n" +
                              "12. Generar cadena\n" +
                              "13. Salir\n");

            String eleccion = Console.ReadLine();
            return eleccion;
        }

        public void LlamarMetodo(String eleccion)
            {
            switch (eleccion)
            {
                case "1":
                    Console.WriteLine("Escriba el nombre el archivo");

                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    break;
                case "10":
                    break;
                case "11":
                    break;
                case "12":
                    break;
            }
        }
    }
}
