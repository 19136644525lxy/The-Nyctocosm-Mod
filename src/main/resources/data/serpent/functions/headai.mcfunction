#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add SerpentMusic timer 1
execute if score SerpentMusic timer matches 1900.. run function serpent:__generated__/block/187
execute if score SerpentHealth value matches ..0 unless score SerpentState value matches 5 run function serpent:defeat
execute at @s unless score SerpentState value matches 5 if entity @e[tag=positionarea,tag=serpent,distance=200..] run scoreboard players set SerpentState value 1
execute at @s facing entity @e[tag=serpent,tag=currentfacing] feet run tp @s ~ ~ ~ ~ ~
execute at @s run tp @e[tag=serpent,tag=currentfacing] ^ ^ ^30
execute at @s facing entity @e[tag=target,tag=serpent] feet run tp @e[tag=serpent,tag=towardfacing] ^ ^ ^30
execute unless block ~ ~ ~ #nyctocosm:air run function serpent:__generated__/block/188
scoreboard players remove SerpentSFX timer 1
execute if score SerpentSFX timer matches ..0 run function serpent:__generated__/block/190
execute at @s if block ~ ~ ~ #nyctocosm:air as @s[tag=inground] run function serpent:__generated__/block/191
execute at @s unless block ~ ~ ~ #nyctocosm:air as @s[tag=!inground] run function serpent:__generated__/block/192
execute at @s if score SerpentState value matches 1 run function serpent:__generated__/block/193
execute if score SerpentState value matches 2 run function serpent:__generated__/block/197
execute if score SerpentState value matches 4 run function serpent:__generated__/block/199