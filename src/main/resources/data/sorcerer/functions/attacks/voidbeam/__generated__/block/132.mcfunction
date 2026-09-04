#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s anchored eyes facing entity @a[gamemode=!spectator,sort=nearest,distance=..40] eyes positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^2 facing entity @s eyes facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
execute at @s run function sorcerer:attacks/voidbeam/raycast
tag @s remove endraycast
scoreboard players reset @s ns.voidbeamrange