#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[predicate=nyctocosm:in_nyctocosm] run function nyctocosm:__generated__/block/1
scoreboard players remove @s tonytimer 1
tag @s remove walk
tag @s[scores={walk=1..}] add walk
execute as @s[tag=!luminous,predicate=nyctocosm:in_nyctocosm] run function nyctocosm:__generated__/block/2
scoreboard players reset @s walk
scoreboard players remove @s[scores={portaltimer=1..}] portaltimer 1
scoreboard players reset @s[scores={portaltimer=..0}] portaltimer
stopsound @s music music.game