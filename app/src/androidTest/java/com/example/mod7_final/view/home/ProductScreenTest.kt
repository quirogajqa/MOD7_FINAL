package com.example.mod7_final.view.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import com.example.mod7_final.MainActivity
import org.junit.Rule
import org.junit.Test

class ProductScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verifyProductListUpdatesWithData() {

        composeRule.onNodeWithText("Lista de productos").assertIsDisplayed()

        composeRule.waitUntil(timeoutMillis = 10000) {
            composeRule.onAllNodesWithText("ID: ", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onAllNodesWithText("ID: ", substring = true)
            .onFirst()
            .assertIsDisplayed()
    }
}