object Compose {

    private const val composeBOMVersion = "2023.03.00"
    const val composeVersion = "1.2.1"
    private const val composeUITooling = "1.2.1"
    const val composeCompilerVersion = "1.4.3"
    private const val navigationVersion = "2.4.0-beta02"
    private const val hiltNavigationComposeVersion = "1.0.0-beta01"
    private const val activityComposeVersion = "1.7.2"
    private const val lifecycleVersion = "2.4.0"
    private const val pagingVersion="1.0.0-alpha17"
    private const val constraintLayoutVersion="1.0.1"
    private const val splashVersion = "1.0.0-beta01"

    const val composeBOM = "androidx.compose:compose-bom:$composeBOMVersion"
    const val composeUI = "androidx.compose.ui:ui"
    const val composeUIGraphic = "androidx.compose.ui:ui-graphics"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"

    const val material="androidx.compose.material:material:$composeVersion"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
    const val paging="androidx.paging:paging-compose:$pagingVersion"
    const val constraintLayout="androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion"
    const val splash = "androidx.core:core-splashscreen:$splashVersion"


    const val composeUnitTest="androidx.compose.ui:ui-test-junit4:$composeUITooling"
    const val composeDebugTest="androidx.compose.ui:ui-tooling:$composeUITooling"
    const val composeDebugTestManifest="androidx.compose.ui:ui-test-manifest:$composeUITooling"
}