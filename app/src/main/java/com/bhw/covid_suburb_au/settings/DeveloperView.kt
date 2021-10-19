package com.bhw.covid_suburb_au.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.AppConfigurations
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.util.OpenExternalLinkHelper

@Composable
fun DeveloperView() {
    val context = LocalContext.current
    Text(
        text = stringResource(R.string.developer),
        color = MaterialTheme.colors.onPrimary,
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.size(8.dp))
    Text(
        text = stringResource(R.string.report_issues),
        style = MaterialTheme.typography.overline,
        color = MaterialTheme.colors.onSecondary,
        textDecoration = TextDecoration.Underline,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                OpenExternalLinkHelper.openLink(context, AppConfigurations.About.GITHUB_NEW_ISSUES)
            }
    )
}