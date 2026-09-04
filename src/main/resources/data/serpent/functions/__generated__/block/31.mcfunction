#built using mc-build (https://github.com/mc-build/mc-build)

playsound nyctocosm:serpent.death master @a[distance=..200] ~ ~ ~ 50
scoreboard players set SerpentState value 5
scoreboard players set SerpentInterp value 3
scoreboard players reset SerpentMusic timer
execute as @a[tag=serpentmusicplaying] run function serpent:__generated__/block/32