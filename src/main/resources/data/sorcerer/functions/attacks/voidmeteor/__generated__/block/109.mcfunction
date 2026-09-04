#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.voidmeteortimer 1
execute as @s[tag=!activated] at @s run function sorcerer:attacks/voidmeteor/__generated__/block/110
execute as @s[tag=activated] at @s run function sorcerer:attacks/voidmeteor/__generated__/block/112