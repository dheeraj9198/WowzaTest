import /usr/local/WowzaStreamingEngine/lib as global library in intellij

main class - com.wowza.wms.bootstrap.Bootstrap
vm options - -Xmx768M -Dcom.wowza.wms.AppHome="/usr/local/WowzaStreamingEngine" -Dcom.wowza.wms.ConfigHome="/usr/local/WowzaStreamingEngine" -Dcom.sun.management.jmxremote=true  -Dcom.wowza.wms.native.base="linux
program arguments - start

in Application.xml include
                         <Module>
                                <Name>Dheeraj</Name>
                                <Description>Dheeraj</Description>
                                <Class>com.test.dheeraj.Test</Class>
                        </Module>

in modules
