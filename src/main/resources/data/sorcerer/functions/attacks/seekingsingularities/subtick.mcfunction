#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s ns.seekingsingularitiestimer 1
execute at @s anchored eyes facing entity @a[sort=nearest,distance=..50,gamemode=!spectator,limit=1] eyes positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^6 facing entity @s eyes facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = seekingsingularitiesdamage value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function sorcerer:attacks/seekingsingularities/__generated__/block/142
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^0.24
execute at @s run function sorcerer:attacks/seekingsingularities/__generated__/block/143
execute at @s positioned ~-0.25 ~-0.25 ~-0.25 if entity @a[dx=0,gamemode=!spectator] positioned ~-0.5 ~-0.5 ~-0.5 if entity @a[dx=0,gamemode=!spectator] at @s run function sorcerer:attacks/seekingsingularities/__generated__/block/144
execute at @s[scores={ns.seekingsingularitiestimer=..0}] run function sorcerer:attacks/seekingsingularities/__generated__/block/145