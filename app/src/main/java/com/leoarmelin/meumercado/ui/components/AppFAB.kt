package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.extensions.fabOption
import com.leoarmelin.meumercado.ui.theme.Blue
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.ui.theme.White
import com.leoarmelin.sharedmodels.navigation.NavDestination

@Composable
fun AppFAB(
    modifier: Modifier = Modifier,
    destinations: List<NavDestination>,
    onSelect: (NavDestination) -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isOpen, label = "isOpen")
    val angle by transition.animateFabIconAngle()

    transition.AnimateFabMenuVisibility(modifier.padding(bottom = 72.dp)) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            items(destinations) { destination ->
                DestinationItem(
                    destination = destination,
                    onSelect = {
                        onSelect(it)
                        isOpen = false
                    }
                )
            }
        }
    }

    AnimateFabVisibility(modifier, destinations.isNotEmpty()) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = Strings.FAB.iconDescription,
            tint = White,
            modifier = Modifier
                .size(56.dp)
                .rotate(angle)
                .clip(CircleShape)
                .background(Blue)
                .clickable { isOpen = !isOpen }
        )
    }
}

@Composable
private fun DestinationItem(destination: NavDestination, onSelect: (NavDestination) -> Unit) {
    Text(
        destination.fabOption,
        color = White,
        fontSize = 16.sp,
        modifier = Modifier
            .background(Blue, RoundedCornerShape(32.dp))
            .clickable { onSelect(destination) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun Transition<Boolean>.animateFabIconAngle() = this.animateFloat(
    label = "angle",
    transitionSpec = { spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow) }
) {
    if (it) 45f else 0f
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Transition<Boolean>.AnimateFabMenuVisibility(
    modifier: Modifier,
    content: @Composable () -> Unit
) =
    this.AnimatedVisibility(
        modifier = modifier,
        visible = { it },
        enter = slideInVertically(
            animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow)
        ) { 300 } + fadeIn(tween()),
        exit =
        slideOutVertically(
            animationSpec = tween(500)
        ) { 600 } + fadeOut(tween(250)),
    ) { content() }

@Composable
fun AnimateFabVisibility(modifier: Modifier, isVisible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(visible = isVisible, modifier = modifier) {
        content()
    }
}

@Preview
@Composable
private fun PreviewOne() {
    Box(modifier = Modifier.fillMaxSize()) {
        AppFAB(
            modifier = Modifier
                .padding(bottom = 32.dp, end = 16.dp)
                .align(Alignment.BottomEnd),
            destinations = listOf(NavDestination.NewCategory, NavDestination.Camera),
            onSelect = {}
        )
    }
}