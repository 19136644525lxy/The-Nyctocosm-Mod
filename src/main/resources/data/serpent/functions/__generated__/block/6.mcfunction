#built using mc-build (https://github.com/mc-build/mc-build)

bossbar set minecraft:serpent players @a[distance=..200]
execute as @s[tag=head] at @s run function serpent:__generated__/block/7
execute as @s[tag=hitbox] at @s run function serpent:__generated__/block/22
execute as @s[tag=beam] at @s run function serpent:__generated__/block/24
execute as @s[tag=ai] at @s run function serpent:ai
execute as @s[tag=tail] at @s run function serpent:__generated__/block/28
execute at @s[tag=1] run tp @e[tag=hitbox,tag=serpent,tag=1] ~ ~ ~
execute at @s[tag=2] run tp @e[tag=hitbox,tag=serpent,tag=2] ~ ~ ~
execute at @s[tag=3] run tp @e[tag=hitbox,tag=serpent,tag=3] ~ ~ ~
execute at @s[tag=4] run tp @e[tag=hitbox,tag=serpent,tag=4] ~ ~ ~
execute at @s[tag=5] run tp @e[tag=hitbox,tag=serpent,tag=5] ~ ~ ~
execute at @s[tag=6] run tp @e[tag=hitbox,tag=serpent,tag=6] ~ ~ ~
execute at @s[tag=7] run tp @e[tag=hitbox,tag=serpent,tag=7] ~ ~ ~
execute at @s[tag=8] run tp @e[tag=hitbox,tag=serpent,tag=8] ~ ~ ~
execute at @s[tag=9] run tp @e[tag=hitbox,tag=serpent,tag=9] ~ ~ ~
execute at @s[tag=10] run tp @e[tag=hitbox,tag=serpent,tag=10] ~ ~ ~
execute at @s[tag=11] run tp @e[tag=hitbox,tag=serpent,tag=11] ~ ~ ~
execute at @s[tag=12] run tp @e[tag=hitbox,tag=serpent,tag=12] ~ ~ ~
execute at @s[tag=13] run tp @e[tag=hitbox,tag=serpent,tag=13] ~ ~ ~
execute at @s[tag=14] run tp @e[tag=hitbox,tag=serpent,tag=14] ~ ~ ~
execute at @s[tag=15] run tp @e[tag=hitbox,tag=serpent,tag=15] ~ ~ ~
execute at @s[tag=16] run tp @e[tag=hitbox,tag=serpent,tag=16] ~ ~ ~
execute at @s[tag=17] run tp @e[tag=hitbox,tag=serpent,tag=17] ~ ~ ~
execute at @s[tag=18] run tp @e[tag=hitbox,tag=serpent,tag=18] ~ ~ ~
execute at @s[tag=19] run tp @e[tag=hitbox,tag=serpent,tag=19] ~ ~ ~
execute at @s[tag=20] run tp @e[tag=hitbox,tag=serpent,tag=20] ~ ~ ~
execute at @s[tag=21] run tp @e[tag=hitbox,tag=serpent,tag=21] ~ ~ ~
execute at @s[tag=22] run tp @e[tag=hitbox,tag=serpent,tag=22] ~ ~ ~
execute at @s[tag=23] run tp @e[tag=hitbox,tag=serpent,tag=23] ~ ~ ~
execute at @s[tag=24] run tp @e[tag=hitbox,tag=serpent,tag=24] ~ ~ ~
execute at @s[tag=25] run tp @e[tag=hitbox,tag=serpent,tag=25] ~ ~ ~
execute at @s[tag=26] run tp @e[tag=hitbox,tag=serpent,tag=26] ~ ~ ~
execute at @s[tag=27] run tp @e[tag=hitbox,tag=serpent,tag=27] ~ ~ ~
execute at @s[tag=28] run tp @e[tag=hitbox,tag=serpent,tag=28] ~ ~ ~
execute at @s[tag=29] run tp @e[tag=hitbox,tag=serpent,tag=29] ~ ~ ~
execute at @s[tag=30] run tp @e[tag=hitbox,tag=serpent,tag=30] ~ ~ ~
execute at @s[tag=31] run tp @e[tag=hitbox,tag=serpent,tag=31] ~ ~ ~
execute at @s[tag=32] run tp @e[tag=hitbox,tag=serpent,tag=32] ~ ~ ~
execute at @s[tag=33] run tp @e[tag=hitbox,tag=serpent,tag=33] ~ ~ ~
execute at @s[tag=34] run tp @e[tag=hitbox,tag=serpent,tag=34] ~ ~ ~
execute at @s[tag=35] run tp @e[tag=hitbox,tag=serpent,tag=35] ~ ~ ~
execute at @s[tag=36] run tp @e[tag=hitbox,tag=serpent,tag=36] ~ ~ ~
execute at @s[tag=37] run tp @e[tag=hitbox,tag=serpent,tag=37] ~ ~ ~
execute at @s[tag=38] run tp @e[tag=hitbox,tag=serpent,tag=38] ~ ~ ~
execute at @s[tag=39] run tp @e[tag=hitbox,tag=serpent,tag=39] ~ ~ ~
execute at @s[tag=40] run tp @e[tag=hitbox,tag=serpent,tag=40] ~ ~ ~
function serpent:move
execute store result score Temp s.yTilt run data get entity @s Rotation[1] 10
execute if score Temp s.yTilt matches ..-1 run scoreboard players add Temp s.yTilt 3600
execute store result entity @s Pose.Head.[0] float .1 run scoreboard players get Temp s.yTilt