package com.pablodev.shamovie.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pablodev.shamovie.core.presentation.MediaList
import com.pablodev.shamovie.core.presentation.MediaSearchBar
import com.pablodev.shamovie.core.util.toJson
import com.pablodev.shamovie.media.domain.MediaResult
import com.pablodev.shamovie.media.presentation.detail.OriginRoute
import com.pablodev.shamovie.media.presentation.search.SearchAction
import com.pablodev.shamovie.media.presentation.search.SearchViewModel
import com.pablodev.shamovie.navigation.Route
import com.pablodev.shamovie.ui.theme.Gay
import com.pablodev.shamovie.ui.theme.Greenish
import com.pablodev.shamovie.ui.theme.Orangish
import com.pablodev.shamovie.ui.theme.TextGray

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavController,
    query: String? = null
) {

    val state by viewModel.state.collectAsStateWithLifecycle()


    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 }
    val searchResultsListState = rememberLazyListState()
    val favoriteBooksListState = rememberLazyListState()

    LaunchedEffect(state.movieResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.onAction(SearchAction.OnTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier
            .background(Greenish)
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MediaSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                viewModel.onAction(SearchAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            //color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier
                        .padding(bottom = 0.dp, top = 8.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = Orangish,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            viewModel.onAction(SearchAction.OnTabSelected(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Gay
                    ) {
                        Text(
                            text = "Movies",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            viewModel.onAction(SearchAction.OnTabSelected(1))

                        },
                        modifier = Modifier
                            .weight(1f),
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Gay
                    ) {
                        Text(
                            text = "Tv Shows",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIndex ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when(pageIndex) {
                            0 -> {
                                if(state.isLoading) {
                                    CircularProgressIndicator(
                                        color = Orangish
                                    )
                                } else {
                                    when {
//                                        state.errorMessage != null -> {
//                                            Text(
//                                                text = state.errorMessage.asString(),
//                                                textAlign = TextAlign.Center,
//                                                style = MaterialTheme.typography.headlineSmall,
//                                                color = MaterialTheme.colorScheme.error
//                                            )
//                                        }
                                        state.movieResults.isEmpty() -> {
                                            Text(
                                                text = "No movies found",
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = TextGray
                                            )
                                        }
                                        else -> {
                                            MediaList(
                                                mediaList = state.movieResults,
                                                onMediaClick = {
                                                    navController.navigate(
                                                        Route.Details(
                                                            query = null,
                                                            mediaId = it.id.toString(),
                                                            isMovie = it is MediaResult.Movie,
                                                            originRoute = OriginRoute.SEARCH.toJson()
                                                        )
                                                    )
                                                },
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(bottom = 64.dp),
                                                scrollState = searchResultsListState,
                                                isSearchResult = true
                                            )
                                        }
                                    }
                                }
                            }
                            1 -> {
                                if(state.tvShowsResults.isEmpty()) {
                                    Text(
                                        text = "No tv shows found",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = TextGray
                                    )
                                } else {
                                    MediaList(
                                        mediaList = state.tvShowsResults,
                                        onMediaClick = {
                                            navController.navigate(
                                                Route.Details(
                                                    query = null,
                                                    mediaId = it.id.toString(),
                                                    isMovie = it is MediaResult.Movie,
                                                    originRoute = OriginRoute.SEARCH.toJson()
                                                )
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(bottom = 48.dp),
                                        scrollState = searchResultsListState,
                                        isSearchResult = true
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}