#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s e.lightningrange 1
execute if score @s e.lightningrange matches ..0 run tag @s add endraycast
execute unless block ^ ^ ^0.5 #nyctocosm:air run tag @s add endraycast
execute if entity @e[tag=lightningtarget,distance=..2] run tag @s add endraycast
execute as @s[tag=lightningkill] as @e[type=#nyctocosm:mobs,distance=..2] at @s run function effects:lightning/__generated__/block/643
execute if predicate effects:lightning/lightning_zag_chance run function effects:lightning/__generated__/block/644
execute as @s[tag=!endraycast] positioned ^ ^ ^0.5 run function effects:lightning/pathraycast
execute as @s[tag=endraycast] run function effects:lightning/__generated__/block/646