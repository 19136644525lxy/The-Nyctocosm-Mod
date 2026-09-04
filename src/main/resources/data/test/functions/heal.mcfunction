#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score OGMaxHealth value run data get entity @s Health 1000
scoreboard players operation 0 temp = OGMaxHealth value
scoreboard players add 0 temp 3000
scoreboard players operation @s value = 0 temp
attribute @s minecraft:generic.max_health base set 18
effect give @s instant_health 1 28 true
schedule function test:__generated__/schedule/0 2t replace