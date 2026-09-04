#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s spelltimer 1
fill ~ ~ ~ ~ ~1 ~ light[level=15] replace air
execute if score @s spelltimer matches 2.. run function spells:__generated__/block/43