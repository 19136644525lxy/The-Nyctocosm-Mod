#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @a[tag=damagetemp,limit=1] add damager
execute as @s run function spells:__generated__/block/56
effect give @s darkness 10 0 true
playsound nyctocosm:spells.impact.abhorrence master @a[distance=..10] ~ ~ ~ 5