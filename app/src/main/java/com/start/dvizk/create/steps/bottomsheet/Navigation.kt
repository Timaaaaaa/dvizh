package com.start.dvizk.create.steps.bottomsheet

sealed class Navigation {

    object onSubCategoryBack : Navigation()

    object onCategoryBack : Navigation()
}