#built using mc-build (https://github.com/mc-build/mc-build)

execute at @a[distance=..300,gamemode=!spectator,tag=!emissarydebug,limit=1,sort=random] run summon marker ~ ~100 ~ {Tags:["emissary","attack","celestial_smite","tear","justsummoned"]}
execute as @e[tag=attack,tag=emissary,tag=celestial_smite,tag=tear,tag=justsummoned] at @s run function emissary:attacks/phase2/celestial_smite/__generated__/block/389