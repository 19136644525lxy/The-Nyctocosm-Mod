#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set @s timer 900
execute as @s[tag=!z] run particle flash ~ ~ ~ 0.1 2 5 0 100 force
execute as @s[tag=z] run particle flash ~ ~ ~ 5 2 0.1 0 100 force
execute as @s[tag=!z] run particle minecraft:firework ~ ~ ~ 0.1 1 2 1 500 force
execute as @s[tag=z] run particle minecraft:firework ~ ~ ~ 2 1 0.1 1 500 force
summon minecraft:creeper ~ ~ ~ {Fuse:0,ignited:1b,Silent:1b,CustomName:'{"translate":"spell.resonance.collapse"}'}
execute rotated ~ 0 positioned ^ ^ ^3 rotated ~ 90 run function effects:shockwave/resonance_portal
execute rotated ~ 0 positioned ^ ^ ^-3 rotated ~ 90 run function effects:shockwave/resonance_portal
kill @e[tag=portal,tag=third,tag=external_resonance]
tag @e[tag=portal,tag=origin,tag=activating] remove activating
function effects:soul_swirl/explode
execute as @s[tag=!z] run function nyctocosm:portal/activation/summon_surface
execute as @s[tag=z] run function nyctocosm:portal/activation/z/summon_surface
playsound nyctocosm:portal.external_resonance.collapse.third master @a[distance=..100] ~ ~ ~ 50
execute as @a[distance=..50] at @s run function nyctocosm:portal/__generated__/block/65