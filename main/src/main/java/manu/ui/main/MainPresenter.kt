/*
 * Copyright (c) 2012-2018 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package manu.ui.main

class MainPresenter(val mView: MainContract.View) : MainContract.Presenter {

    private lateinit var mInteractor: MainContract.Interactor

    override fun onCreate() {

        mInteractor = MainInteractor()

        mView.setUpUi()

    }

}