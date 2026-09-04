#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s em.timer 1
execute if score @s em.timer matches ..0 run function emissary:attacks/phase2/flame_geysers/shoot
execute as @s[scores={em.timer=20}] at @s run playsound nyctocosm:emissary.attack.flame_geysers.erupt master @a[distance=..300] ~ ~ ~ 1.5
execute as @s[scores={em.timer=..40}] at @s run function emissary:attacks/phase2/flame_geysers/__generated__/block/404