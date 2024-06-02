package com.example.searchimagesaritasa.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.searchimagesaritasa.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    historyList: List<String>
) {
    var active by remember { mutableStateOf(false) }

    DockedSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text(stringResource(R.string.search_hint))
        },
        modifier = if (query.isNotEmpty() || historyList.isEmpty()) Modifier
            .fillMaxWidth()
            .height(55.dp) else Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear search",
                    modifier = Modifier.clickable { onQueryChange("") }
                )
            }
        },
        content = {
            if (active && query.isEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(historyList) { item ->
                        ListItem(
                            leadingContent = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.clickable {
                                onSearch(item)
                                onQueryChange(item)
                                active = false
                            },
                            headlineContent = { Text(text = item) }
                        )
                    }
                }
            }
        }
    )
}
