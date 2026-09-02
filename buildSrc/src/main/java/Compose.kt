object Compose {

    private const val composeBOMVersion = "2026.08.00"
    private const val navigationVersion = "2.10.0"
    private const val hiltNavigationComposeVersion = "1.4.0"
    private const val activityComposeVersion = "1.13.0"
    private const val lifecycleVersion = "2.11.0"
    private const val pagingVersion = "3.5.1"
    private const val constraintLayoutVersion = "1.1.2"
    private const val splashVersion = "1.2.0"

    // Material icons were split out of androidx.compose.material and are no longer
    // part of the Compose BOM; 1.7.8 is the last published release.
    private const val materialIconsVersion = "1.7.8"

    const val composeBOM = "androidx.compose:compose-bom:$composeBOMVersion"
    const val composeUI = "androidx.compose.ui:ui"
    const val composeUIGraphic = "androidx.compose.ui:ui-graphics"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"

    const val material = "androidx.compose.material:material"
    const val materialIcons = "androidx.compose.material:material-icons-core:$materialIconsVersion"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
    const val paging = "androidx.paging:paging-compose:$pagingVersion"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion"
    const val splash = "androidx.core:core-splashscreen:$splashVersion"

    const val composeUnitTest = "androidx.compose.ui:ui-test-junit4"
    const val composeDebugTest = "androidx.compose.ui:ui-tooling"
    const val composeDebugTestManifest = "androidx.compose.ui:ui-test-manifest"
}
