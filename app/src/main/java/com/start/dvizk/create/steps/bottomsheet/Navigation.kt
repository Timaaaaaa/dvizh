package com.start.dvizk.create.steps.bottomsheet

sealed class Navigation {

    object OnSubCategoryBack : Navigation()

    object OnCategoryBack : Navigation()
}