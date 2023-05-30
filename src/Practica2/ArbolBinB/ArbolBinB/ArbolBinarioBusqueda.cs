using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ArbolBinB
{
    class ArbolBinarioBusqueda : NodoArbol
    {
        NodoArbol raiz; 

        public ArbolBinarioBusqueda()
        {
            raiz = null;
        }

        public void Insertar(int info)
        {
            NodoArbol nuevo= new NodoArbol();
            nuevo.info = info;
            nuevo.izq = null;
            nuevo.der = null;
            if (raiz == null)
                raiz = nuevo;
            else
            {
                NodoArbol anterior = null, recorrer;
                recorrer = raiz;
                while (recorrer != null)
                {
                    anterior = recorrer;
                    if (info < recorrer.info)
                        recorrer = recorrer.izq;
                    else
                        recorrer = recorrer.der;
                }
                if (info < anterior.info)
                    anterior.izq = nuevo;
                else
                    anterior.der = nuevo;
            }
        } 

        private void ImprimirPre(NodoArbol reco)
        {
            if (reco != null)
            {
                Console.Write(reco.info + " ");
                ImprimirPre(reco.izq);
                ImprimirPre(reco.der);
            }
        }

        public void ImprimirPre()
        {
            ImprimirPre(raiz);
            Console.WriteLine();
        }

        private void ImprimirEntre(NodoArbol reco)
        {
            if (reco != null)
            {
                ImprimirEntre(reco.izq);
                Console.Write(reco.info + " ");
                ImprimirEntre(reco.der);
            }
        }

        public void ImprimirEntre()
        {
            ImprimirEntre(raiz);
            Console.WriteLine();
        }

        private void ImprimirPost(NodoArbol reco)
        {
            if (reco != null)
            {
                ImprimirPost(reco.izq);
                ImprimirPost(reco.der);
                Console.Write(reco.info + " ");
            }
        }

        public void ImprimirPost()
        {
            ImprimirPost(raiz);
            Console.WriteLine();
        }

    }
}
