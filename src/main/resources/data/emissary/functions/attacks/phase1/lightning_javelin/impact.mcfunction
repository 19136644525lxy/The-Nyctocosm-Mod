#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=impact] positioned ~-0.5 ~-0.5 ~-0.5 as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] at @s run function emissary:attacks/phase1/lightning_javelin/__generated__/block/54
execute as @s[tag=impact] run summon lightning_bolt
kill @s