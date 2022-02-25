import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun Cell(
    world: MutableList<MutableList<Boolean>>,
    row: Int,
    column: Int,
    cellsHavePadding: Boolean,
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(12.dp, 12.dp)
) {
    Box(modifier = modifier.size(size)) {
        Canvas(
            modifier = Modifier.fillMaxSize()
                .padding(if (cellsHavePadding) 0.5.dp else 0.0.dp)
        ) {
            drawRect(
                if (world[row][column]) Color.Cyan else Color.Black,
                size = this.size
            )
        }
    }
}