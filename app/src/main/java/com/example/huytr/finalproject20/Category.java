package com.example.huytr.finalproject20;

import java.util.HashMap;

/**
 * Created by huytr on 22-08-2015.
 */
public class Category {

    public static Category chosen;
        public static HashMap<Long , Category> list = null;

            public int offset;
        public int type;
            public String name;
        public int category;

        public Category(String name,
                        int type,
                        int category,
                        int offset)
        {
                this.name = name;
                this.type = type;
                this.offset = offset;
                this.category = category;
        }

    public static String listName[] = {
            "Animal" , "City" , "Culture" , "Do It Yourself" , "Food & Drink" , "Entertainment" , "Health Care" , "Household" , "Music" , "Shopping" ,
    };

    public static int[] animal = {
            R.drawable.icon_animal01,
            R.drawable.icon_animal02,
            R.drawable.icon_animal03,
            R.drawable.icon_animal04,
            R.drawable.icon_animal05,
            R.drawable.icon_animal06,
            R.drawable.icon_animal07,
            R.drawable.icon_animal08,
            R.drawable.icon_animal09,
            R.drawable.icon_animal10
    };
    public static int[] city = {
            R.drawable.icon_city01,
            R.drawable.icon_city02,
            R.drawable.icon_city03,
            R.drawable.icon_city04,
            R.drawable.icon_city05,
            R.drawable.icon_city06,
            R.drawable.icon_city07,
            R.drawable.icon_city08,
            R.drawable.icon_city09,
            R.drawable.icon_city10
    };
    public static int[] culture = {
            R.drawable.icon_culture01,
            R.drawable.icon_culture02,
            R.drawable.icon_culture03,
            R.drawable.icon_culture04,
            R.drawable.icon_culture05,
            R.drawable.icon_culture06,
            R.drawable.icon_culture07,
            R.drawable.icon_culture08,
            R.drawable.icon_culture09,
            R.drawable.icon_culture10
    };
    public static int[] diy = {
            R.drawable.icon_diy01,
            R.drawable.icon_diy02,
            R.drawable.icon_diy03,
            R.drawable.icon_diy04,
            R.drawable.icon_diy05,
            R.drawable.icon_diy06,
            R.drawable.icon_diy07,
            R.drawable.icon_diy08,
            R.drawable.icon_diy09,
            R.drawable.icon_diy10,
            R.drawable.icon_diy11,
            R.drawable.icon_diy12,
            R.drawable.icon_diy13,
            R.drawable.icon_diy14,
            R.drawable.icon_diy15,
            R.drawable.icon_diy16,
            R.drawable.icon_diy17,
            R.drawable.icon_diy18,
            R.drawable.icon_diy19,
            R.drawable.icon_diy20,
            R.drawable.icon_diy21,
            R.drawable.icon_diy22,
            R.drawable.icon_diy23,
            R.drawable.icon_diy24,
            R.drawable.icon_diy25,
            R.drawable.icon_diy26
    };
    public static int[] fd = {
            R.drawable.icon_fd01,
            R.drawable.icon_fd02,
            R.drawable.icon_fd03,
            R.drawable.icon_fd04,
            R.drawable.icon_fd05,
            R.drawable.icon_fd06,
            R.drawable.icon_fd07,
            R.drawable.icon_fd08,
            R.drawable.icon_fd09,
            R.drawable.icon_fd10,
            R.drawable.icon_fd11,
            R.drawable.icon_fd12,
            R.drawable.icon_fd13,
            R.drawable.icon_fd14,
            R.drawable.icon_fd15,
            R.drawable.icon_fd16,
            R.drawable.icon_fd17,
            R.drawable.icon_fd18,
            R.drawable.icon_fd19,
            R.drawable.icon_fd20,
            R.drawable.icon_fd21,
            R.drawable.icon_fd22,
            R.drawable.icon_fd23,
            R.drawable.icon_fd24,
            R.drawable.icon_fd25,
            R.drawable.icon_fd26,
            R.drawable.icon_fd27,
            R.drawable.icon_fd28,
            R.drawable.icon_fd29,
            R.drawable.icon_fd30,
            R.drawable.icon_fd31,
            R.drawable.icon_fd32,
            R.drawable.icon_fd33,
            R.drawable.icon_fd34,
            R.drawable.icon_fd35,
            R.drawable.icon_fd36,
            R.drawable.icon_fd37,
            R.drawable.icon_fd38
    };
    public static int[] entertainment = {
            R.drawable.icon_entertainment01,
            R.drawable.icon_entertainment02,
            R.drawable.icon_entertainment03,
            R.drawable.icon_entertainment04,
            R.drawable.icon_entertainment05
    };
    public static int[] healthcare = {
            R.drawable.icon_healthcare01,
            R.drawable.icon_healthcare02,
            R.drawable.icon_healthcare03,
            R.drawable.icon_healthcare04,
            R.drawable.icon_healthcare05,
            R.drawable.icon_healthcare06,
            R.drawable.icon_healthcare07,
            R.drawable.icon_healthcare08,
            R.drawable.icon_healthcare09,
            R.drawable.icon_healthcare10,
            R.drawable.icon_healthcare11,
            R.drawable.icon_healthcare12,
            R.drawable.icon_healthcare13,
            R.drawable.icon_healthcare14,
            R.drawable.icon_healthcare15,
            R.drawable.icon_healthcare16,
            R.drawable.icon_healthcare17,
            R.drawable.icon_healthcare18,
            R.drawable.icon_healthcare19,
            R.drawable.icon_healthcare20,
            R.drawable.icon_healthcare21,
            R.drawable.icon_healthcare22,
            R.drawable.icon_healthcare23,
            R.drawable.icon_healthcare24,
            R.drawable.icon_healthcare25,
            R.drawable.icon_healthcare26
    };
    public static int[] household = {
            R.drawable.icon_household01,
            R.drawable.icon_household02,
            R.drawable.icon_household03,
            R.drawable.icon_household04,
            R.drawable.icon_household05,
            R.drawable.icon_household06,
            R.drawable.icon_household07,
            R.drawable.icon_household08,
            R.drawable.icon_household09,
            R.drawable.icon_household10,
            R.drawable.icon_household11,
            R.drawable.icon_household12,
            R.drawable.icon_household13,
            R.drawable.icon_household14,
            R.drawable.icon_household15,
            R.drawable.icon_household16
    };

    public static int[] music = {
            R.drawable.icon_music01,
            R.drawable.icon_music02,
            R.drawable.icon_music03,
            R.drawable.icon_music04,
            R.drawable.icon_music05,
            R.drawable.icon_music06,
            R.drawable.icon_music07,
            R.drawable.icon_music08,
            R.drawable.icon_music09,
            R.drawable.icon_music10,
            R.drawable.icon_music11,
            R.drawable.icon_music12,
            R.drawable.icon_music13,
            R.drawable.icon_music14,
            R.drawable.icon_music15,
            R.drawable.icon_music16,
            R.drawable.icon_music17,
            R.drawable.icon_music18,
            R.drawable.icon_music19,
            R.drawable.icon_music20,
            R.drawable.icon_music21,
            R.drawable.icon_music22
    };
    public static int[] shopping = {
            R.drawable.icon_shopping01,
            R.drawable.icon_shopping02,
            R.drawable.icon_shopping03,
            R.drawable.icon_shopping04,
            R.drawable.icon_shopping05,
            R.drawable.icon_shopping06,
            R.drawable.icon_shopping07,
            R.drawable.icon_shopping08,
            R.drawable.icon_shopping09,
            R.drawable.icon_shopping10
    };

    public static int[] calendar = {
            R.drawable.calendar01,
            R.drawable.calendar02,
            R.drawable.calendar03,
            R.drawable.calendar04,
            R.drawable.calendar05,
            R.drawable.calendar06,
            R.drawable.calendar07,
            R.drawable.calendar08,
            R.drawable.calendar09,
            R.drawable.calendar10,
            R.drawable.calendar11,
            R.drawable.calendar12,
            R.drawable.calendar13,
            R.drawable.calendar14,
            R.drawable.calendar15,
            R.drawable.calendar16,
            R.drawable.calendar17,
            R.drawable.calendar18,
            R.drawable.calendar19,
            R.drawable.calendar20,
            R.drawable.calendar21,
            R.drawable.calendar22,
            R.drawable.calendar23,
            R.drawable.calendar24,
            R.drawable.calendar25,
            R.drawable.calendar26,
            R.drawable.calendar27,
            R.drawable.calendar28,
            R.drawable.calendar29,
            R.drawable.calendar30,
            R.drawable.calendar31
    };

    public static int[][] rootResource = {
            animal , city , culture , diy , fd , entertainment , healthcare , household , music , shopping
    };
}
