#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s e.lightningrange 1
execute if score @s e.lightningrange matches ..0 run tag @s add endraycast
execute unless block ^ ^ ^0.5 #nyctocosm:air run tag @s add endraycast
execute if entity @e[tag=lightningtarget,distance=..2] run tag @s add endraycast
execute as @s[tag=lightningkill] as @e[type=#nyctocosm:mobs,distance=..2] at @s run function effects:lightning/__generated__/block/648
execute if predicate effects:lightning/lightning_zag_chance run function effects:lightning/__generated__/block/649
execute as @s[tag=!endraycast] at @s anchored eyes facing entity @e[tag=lightningtarget,limit=1,sort=nearest] feet positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^2 facing entity @s eyes facing ^ ^ ^-1 positioned as @s positioned ^ ^ ^0.5 run function effects:lightning/curvepathraycast
execute as @s[tag=endraycast] run function effects:lightning/__generated__/block/651