#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s timer 1
execute as @s[scores={timer=..0}] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/24
execute at @s facing entity @a[sort=nearest,limit=1] feet run tp @s ^ ^ ^0.2
effect give @a[distance=..1] wither 10 0
effect give @a[distance=..1] instant_damage
execute at @s rotated ~ 0 positioned ~ ~1.625 ~ positioned ^-0.1 ^ ^0.5 run particle dust 0.561 0.000 0.000 0.5 ~ ~ ~ 0 0 0 0 10 normal
execute at @s rotated ~ 0 positioned ~ ~1.625 ~ positioned ^0.1 ^ ^0.5 run particle dust 0.561 0.000 0.000 0.5 ~ ~ ~ 0 0 0 0 10 normal
execute if entity @a[distance=..1] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/25