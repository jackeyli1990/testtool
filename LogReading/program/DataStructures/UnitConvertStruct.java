package DataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class UnitConvertStruct
{
    public static class UnitGroupItem
    {
        public double value;
        public String name;
        public UnitGroupItem(double v,String n)
        {
            value = v;
            name = n;
        }
    }
    public static class UnitGroup
    {
        private ArrayList<UnitGroupItem> al;
        private int ns;
        public UnitGroup(int name_size)
        {
            al = new ArrayList<UnitGroupItem>();
            ns = name_size;
          /*  HashMap<Object,Object> t;
            Hashtable<Object,Object> ht;
            ht.*/
        }
    }
    public static class UnitGroupCenter
    {
        private ArrayList<UnitGroup> al;
        public UnitGroupCenter()
        {
            al = new ArrayList<UnitGroup>();
        }
        protected int FindPosition(int size)
        {
            for(int i = 0; i < al.size(); i ++)
            {
                if(size >= al.get(i).ns)
                {
                    if(size > al.get(i).ns)
                        return i;
                    else
                    {
                        return -1 + i * (-1);
                    }
                }
            }
            if(al.size() == 0)
                return 0;
            else
                return al.size();
        }
        protected void AddUnit(UnitGroupItem ugi)
        {
            int pos = FindPosition(ugi.name.length());
            if(pos >= 0)
            {
                UnitGroup ug = new UnitGroup(ugi.name.length());
                ug.al.add(ugi);
                al.add(pos,ug);
            }
            else
            {
                al.get((pos + 1) * (-1)).al.add(ugi);
            }
        }
        protected UnitGroupItem GetUnit(String s)
        {
            int pos = FindPosition(s.length());
            if(pos >= 0)
                return null;
            UnitGroup ug = al.get((pos + 1) * (-1));
            for(int i = 0; i < ug.al.size(); i ++)
            {
                if(ug.al.get(i).name.equals(s))
                {
                    return ug.al.get(i);
                }
            }
            return null;
        }
        protected List<Integer> UnitLengths()
        {
            ArrayList<Integer> l = new ArrayList<Integer>();
            for(int i = 0; i < al.size(); i ++)
            {
                l.add(al.get(i).ns);
            }
            return l;
        }
        public List<UnitGroupItem> ListUnits()
        {
            ArrayList<UnitGroupItem> l = new ArrayList<UnitGroupItem>();
            for(int i = 0; i < al.size(); i ++)
            {
                UnitGroup ug = al.get(i);
                for(int j = 0; j < ug.al.size(); j ++)
                {
                    l.add(ug.al.get(j));
                }
            }
            return l;
        }
   }
}