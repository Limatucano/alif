package com.tcc.alif.view.ui.administrator.configuration

sealed class ConfigurationIntent{
    object GoToChangePassword : ConfigurationIntent()
    object GoToMyCategories: ConfigurationIntent()
    object GoToProfile: ConfigurationIntent()
    object GoToCompanyProfile: ConfigurationIntent()
    object Exit : ConfigurationIntent()
}
