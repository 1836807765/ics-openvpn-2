/*
 * Copyright (c) 2012-2018 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package manu.ui.main

class MainContract {

    interface View {

        /**
         * Sets up the ui
         */
        fun setUpUi()

    }

    interface Presenter {

        /**
         * Called when view is created
         */
        fun onCreate()

    }

    /**
     * Not used yet
     */
    interface Interactor {}

}