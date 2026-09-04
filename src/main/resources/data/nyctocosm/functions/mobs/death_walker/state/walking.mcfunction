#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove walking.trans
tag @s add walking
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Rot0 value = temp0 rng
scoreboard players operation Rot0 value /= #100000 rng
scoreboard players operation Rot0 value %= #360 rng
scoreboard players add Rot0 value 0
scoreboard players remove Rot0 value 180
execute store result entity @s Rotation[0] float 1 run scoreboard players get Rot0 value
scoreboard players reset Rot0 value
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s timer = temp0 rng
scoreboard players operation @s timer /= #100000 rng
scoreboard players operation @s timer %= #900 rng
scoreboard players add @s timer 100
function animated_java:ajdeathwalker/animations/pause_all
function animated_java:ajdeathwalker/animations/idle/stop
function animated_java:ajdeathwalker/animations/slam/stop
function animated_java:ajdeathwalker/animations/walk/stop
function animated_java:ajdeathwalker/animations/walk/play
tag @s remove idle
tag @s remove following
tag @s remove attacking