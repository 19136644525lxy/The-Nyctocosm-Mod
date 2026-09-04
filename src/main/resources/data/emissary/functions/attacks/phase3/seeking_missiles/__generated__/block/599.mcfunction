#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add damagetemp
tag @e[tag=damagetemp,limit=1] add damager
execute as @a[dx=0,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase3/seeking_missiles/__generated__/block/600
tag @s remove damagetemp