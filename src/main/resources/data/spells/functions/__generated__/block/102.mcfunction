#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add incandescencehit
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
data merge entity @s {Fire:100s}
tag @a[tag=damagetemp,limit=1] add damager
execute as @s run function spells:__generated__/block/103
playsound nyctocosm:spells.impact.incandescence master @a[distance=..10] ~ ~ ~ 5