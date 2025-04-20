package id.idham.gitgud

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SearchScreenUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun typeQuery_displaysSearchResult() {
        composeTestRule
            .onNodeWithTag("search_input", useUnmergedTree = true)
            .performTextInput("idhammi")

        composeTestRule.waitUntil(timeoutMillis = 2_000) {
            composeTestRule
                .onAllNodesWithTag("user_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("idhammi")
            .assertIsDisplayed()
    }

    @Test
    fun clickItem_navigatesToDetail() {
        composeTestRule.waitUntil(timeoutMillis = 2_000) {
            composeTestRule
                .onAllNodesWithTag("user_item")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("mojombo")
            .performClick()

        composeTestRule.waitUntil(2000) {
            composeTestRule
                .onAllNodesWithTag("user_detail_name")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag("user_detail_name", useUnmergedTree = true)
            .assertIsDisplayed()
    }
}
