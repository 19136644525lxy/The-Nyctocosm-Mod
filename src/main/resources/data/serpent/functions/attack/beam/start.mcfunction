#built using mc-build (https://github.com/mc-build/mc-build)

tag @a[tag=serpenttarget] remove serpenttarget
execute at @s run tag @a[distance=..200,limit=1,sort=random] add serpenttarget
scoreboard players set SerpentState value 4
scoreboard players set SerpentAttack value 3
function serpent:attack/twirl
execute at @s run playsound nyctocosm:serpent.scream master @a[distance=..300] ~ ~ ~ 50
execute at @a[tag=serpenttarget,limit=1] run function serpent:attack/beam/__generated__/block/216
execute as @e[tag=beam,tag=justsummoned] run function serpent:attack/beam/__generated__/block/217