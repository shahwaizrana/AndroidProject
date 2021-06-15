package com.example.skincare.Constants

class Constant {

    companion object {
        val CYST = "Cyst";
        val NODULES = "Nodules";
        val SCARS = "Scars";
        val WHITE_HEADS = "Whiteheads";
        val POSTULES = "Pustules";
        val CLEAR_SKIN = "Clear Skin";

        var CYST_INT = true;
        var NODULES_INT = false;
        var SCARS_INT = false;
        var POSTULES_INT = false;
        var WHITE_HEADS_INT = false;
        var CLEAR_SKIN_INT = false;

        var ACNE_CAUSES_TAB=1;
        var ACNE_SYPTOMS_TAB=2;

        fun GET_LOCATION_PERMISSION_REQUEST_CODE(): Int {
            return 1;
        }

        var globalLongitude: Double? = null;
        var gloablLatitude: Double? = null;

    }
}