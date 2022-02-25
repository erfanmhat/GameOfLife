import Constants.WORLD_X_SIZE
import Constants.WORLD_Y_SIZE

fun Boolean.asInt(): Int = if (this) 1 else 0

fun createEmptyWorld(): MutableList<MutableList<Boolean>> {
    val worldXY = mutableListOf<MutableList<Boolean>>()
    for (j in 0 until WORLD_Y_SIZE) {
        val worldX = mutableListOf<Boolean>()
        for (i in 0 until WORLD_X_SIZE) {
            worldX.add(false)
        }
        worldXY.add(worldX)
    }
    return worldXY
}

fun createNextGeneration(world: MutableList<MutableList<Boolean>>): MutableList<MutableList<Boolean>> {
    val newWorld = createEmptyWorld()
    for (row in 0 until WORLD_Y_SIZE) {
        for (column in 0 until WORLD_X_SIZE) {
            newWorld[row][column] = if (world[row][column]) {
                numberOfNeighbours(world, row, column) in 2..3
            } else {
                numberOfNeighbours(world, row, column) == 3
            }
        }
    }
    return newWorld
}

fun numberOfNeighbours(world: MutableList<MutableList<Boolean>>, row: Int, column: Int): Int {
    val rowM1 = (row - 1 + WORLD_Y_SIZE) % WORLD_Y_SIZE
    val rowP1 = (row + 1 + WORLD_Y_SIZE) % WORLD_Y_SIZE
    val columnM1 = (column - 1 + WORLD_X_SIZE) % WORLD_X_SIZE
    val columnP1 = (column + 1 + WORLD_X_SIZE) % WORLD_X_SIZE
    return world[rowM1][columnM1].asInt() + world[rowM1][column].asInt() + world[rowM1][columnP1].asInt() + world[row][columnM1].asInt() + world[row][columnP1].asInt() + world[rowP1][columnM1].asInt() + world[rowP1][column].asInt() + world[rowP1][columnP1].asInt()
}