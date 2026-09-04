#built using mc-build (https://github.com/mc-build/mc-build)

playsound nyctocosm:emissary.attack.godrays.shoot master @a[distance=..200] ~ ~ ~ 50
execute summon marker run function emissary:attacks/phase4/godrays/__generated__/block/670
execute at @e[tag=emissary,tag=attack,tag=godrays,tag=origin,tag=justsummoned] rotated ~45 0 positioned ^ ^ ^4 summon marker run function emissary:attacks/phase4/godrays/__generated__/block/671
execute at @e[tag=emissary,tag=attack,tag=godrays,tag=origin,tag=justsummoned] rotated ~135 0 positioned ^ ^ ^4 summon marker run function emissary:attacks/phase4/godrays/__generated__/block/672
execute at @e[tag=emissary,tag=attack,tag=godrays,tag=origin,tag=justsummoned] rotated ~225 0 positioned ^ ^ ^4 summon marker run function emissary:attacks/phase4/godrays/__generated__/block/673
execute at @e[tag=emissary,tag=attack,tag=godrays,tag=origin,tag=justsummoned] rotated ~315 0 positioned ^ ^ ^4 summon marker run function emissary:attacks/phase4/godrays/__generated__/block/674
tag @e[tag=origin,tag=godrays,tag=attack,tag=emissary] remove justsummoned