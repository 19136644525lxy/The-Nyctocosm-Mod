#built using mc-build (https://github.com/mc-build/mc-build)

playsound nyctocosm:serpent.incur master @a[distance=..100] ~ ~ ~ 20
execute positioned ~ ~-100 ~ run function serpent:summon
execute positioned ~ ~40 ~ run tp @e[tag=positionarea,tag=serpent] ~ ~ ~
scoreboard players set SerpentState value 1
scoreboard players set SerpentSpeed value 2
scoreboard players set SerpentInterp value 1
scoreboard players set SerpentAttack timer 400
scoreboard players set SerpentMusic timer 0
execute as @a[distance=..100] run function serpent:__generated__/block/30