#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove idle.trans
tag @s add idle
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s timer = temp0 rng
scoreboard players operation @s timer /= #100000 rng
scoreboard players operation @s timer %= #100 rng
scoreboard players add @s timer 100
function animated_java:ajdeathwalker/animations/pause_all
function animated_java:ajdeathwalker/animations/slam/stop
function animated_java:ajdeathwalker/animations/idle/stop
function animated_java:ajdeathwalker/animations/idle/play
function animated_java:ajdeathwalker/animations/walk/stop
tag @s remove walking
tag @s remove following
tag @s remove attacking