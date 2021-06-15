package com.example.skincare.Fragments

import com.example.skincare.Constants.Constant

class FragmentModel(var type:Int,var title:String,var description:String) {
lateinit var skinList:List<FragmentModel>;
    companion object {
        fun initialize(acneType: Int): List<FragmentModel> {
            var skinList= ArrayList<FragmentModel>();
            if (Constant.ACNE_CAUSES_TAB==acneType){

                skinList .add(FragmentModel(1,"Overview","The main causes of acne include:"))
                skinList. add(FragmentModel(1,"Clogged pores,caused by things like excess oil production and dead skin cells.","Clogged pores, caused by things like excess oil production and dead skin cells. Sebum is the type of oil released into hair follicles that can become trapped beneath surface of the skin and clog pores."))
                skinList. add(FragmentModel(1,"Hormone fluctuations or imbalances.","For example, when androgen hormones increase oil production rises. This often happens in teens and young adults suffering from acne, especially women experiencing PMS, irregular periods, pregnancy, early menopause, and other hormonal conditions such as poly cystic ovarian syndrome (PCOS)."))
                skinList .add(FragmentModel(1,"Poor diet","Poor diet, such as the “Standard American Diet” that includes lots of refined grains, sugar and unhealthy fats."))
                skinList .add(FragmentModel(1,"Stress","High amount of stress and related problems like psychiatric disorders such as depression and anxiety."))
                skinList .add(FragmentModel(1,"Use of certain medications","Certain medications, including corticosteroids, androgens, birth control pills and lithium (6)."))
            }
            if (Constant.ACNE_SYPTOMS_TAB==acneType){
//syptoms
                skinList. add(FragmentModel(2,"Overview","The most common symptoms that acne causes include:"))
                skinList. add(FragmentModel(2,"Causes Blackheads","Blackheads, or small black dots on the skin, usually around the nose, forehead or chin."))
                skinList. add(FragmentModel(2,"Papules and pustules","Papules and pustule (the technical name for pimples) which cause small or medium sized bumps on the skin that are round, red don’t always have a visible “head”. These are caused by “moderate” types of acne and are not as severe as cysts or nodules."))
                skinList. add(FragmentModel(3,"Cysts or nodules","Cysts or nodules, which are severe pimples that are infected and painful. They can from within deeper layers of the skin, become very swollen or tender, and take longer to heal then papules and pustules."))
                skinList. add(FragmentModel(3,"Dark spots on the skin","Dark spots on the skin (hyperpigmentation). Scars, most often behind from nodules or cysts, especially if they have been “popped” or picked."))
                skinList. add(FragmentModel(3,"Increased sensitivity","Increased sensitivity to products, heat, sweat and sunlight."))


            }
            if (Constant.SCARS_INT){
//prevention
                skinList. add(FragmentModel(3,"Overview","Keep your face clean. whether or not you have acne, its important to wash your face twice daily to remove impurities, dead skin cells, and extra oil from your skin’s surface \n" +
                        "Moisturize …                                     \n" +
                        "Try an over-the counter acne product.\n" +
                        "Use makeup sparingly.\n" +
                        "Watch what you put on your hair."))
                skinList. add(FragmentModel(3,"causes","remedies"))
                skinList. add(FragmentModel(3,"causes","remedies"))
            }

            if (Constant.POSTULES_INT){

                skinList. add(FragmentModel(4,"causes","remedies"))
                skinList. add(FragmentModel(4,"causes","remedies"))
                skinList. add(FragmentModel(4,"causes","remedies"))
            }
            if (Constant.WHITE_HEADS_INT){

                skinList. add(FragmentModel(5,"causes","remedies"))
                skinList. add(FragmentModel(5,"causes","remedies"))
                skinList. add(FragmentModel(5,"causes","remedies"))

            }
            return skinList;

        }
    }
}