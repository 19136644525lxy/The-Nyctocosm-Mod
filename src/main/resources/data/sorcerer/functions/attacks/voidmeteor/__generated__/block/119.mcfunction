#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.voidmeteortimer 1
tag @s[scores={ns.voidmeteortimer=25..}] add explode
function sorcerer:attacks/voidmeteor/procast
tag @s remove endcast
execute at @s[tag=explode] run function sorcerer:attacks/voidmeteor/impact