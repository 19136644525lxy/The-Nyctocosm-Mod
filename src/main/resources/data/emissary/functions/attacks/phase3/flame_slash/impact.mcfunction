#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add damagetemp
scoreboard players operation @s ncd_damage = flameslashdamage em.value
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @e[tag=damagetemp,limit=1] add damager
execute as @a[tag=impact_flame_slash] run function emissary:attacks/phase3/flame_slash/__generated__/block/589
tag @s remove damagetemp
tag @a remove impact_flame_slash
kill @s